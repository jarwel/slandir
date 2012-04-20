class Grievance
  attr_accessor :id, :accountId, :personId, :author, :description, :created

  def initialize(hash)
    hash.each { |k, v| self.send("#{k}=".to_sym, v) if defined?("#{k}=".to_sym) }
    @created = DateTime.parse(@created) if !@created.blank?
  end

  def save
    self.id = UUIDTools::UUID.timestamp_create.to_s if self.id.nil?
    hash = {
      :id => id,
      :accountId => accountId,
      :personId => personId,
      :author => author,
      :description => description,
      :created => created
    }
    response = HTTPClient.new.post(Grievance.service_url, hash.to_json, {"Content-Type" => "application/json"})
    raise "Error creating submit: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.fetchByPerson(personId)
    response = HTTPClient.new.get(Grievance.service_url, { :personId => personId })
    return ActiveSupport::JSON.decode(response.body).map { |props| Grievance.new(props) } if response.status == HTTP::Status::OK
    raise "Error fetching grievances: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.fetchByAccount(accountId)
    response = HTTPClient.new.get(Grievance.service_url, { :accountId => accountId })
    return ActiveSupport::JSON.decode(response.body).map { |props| Grievance.new(props) } if response.status == HTTP::Status::OK
    raise "Error fetching grievances: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.remove(grievanceId)
    response = HTTPClient.new.delete("#{Grievance.service_url}/#{grievanceId}")
    raise "Error deleting grievance: #{response.body}" if response.status != HTTP::Status::OK
  end

  private
  def self.service_url
    return "#{Discovery.lookup("submission", "general")}/v1/grievance"
  end

end