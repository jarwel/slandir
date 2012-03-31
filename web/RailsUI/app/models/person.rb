class Person
  attr_accessor :id, :firstName, :middleName, :lastName, :gender, :birthDate, :phone, :email, :address

  def initialize(hash)
    hash.each { |k, v| self.send("#{k}=".to_sym, v) if defined?("#{k}=".to_sym) }
    @address = Address.new(@address) if !@address.blank?
    @birthDate = Date.parse(@birthDate) if !@birthDate.blank?
  end

  def save
    self.id = UUIDTools::UUID.timestamp_create.to_s if self.id.blank?
    hash = {
      :id => id,
      :firstName => firstName,
      :middleName => middleName,
      :lastName => lastName,
      :gender => gender,
      :birthDate => birthDate.to_s,
      :phone => phone,
      :email => email,
      :address => address.to_hash
    }
    response = HTTPClient.new.post(Person.service_url, hash.to_json, {"Content-Type" => "application/json"})
    raise "Error saving person: #{response.body}" if response.status != HTTP::Status::OK
  end

  def update
    hash = {
      :id => id,
      :firstName => firstName,
      :middleName => middleName,
      :lastName => lastName,
      :gender => gender,
      :birthDate => birthDate.to_s,
      :phone => phone,
      :email => email,
      :address => address.to_hash
    }
    response = HTTPClient.new.put(Person.service_url, hash.to_json, {"Content-Type" => "application/json"})
    raise "Error updating person: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.get(id)
    response = HTTPClient.new.get("#{Person.service_url}/#{id}")
    return Person.new(ActiveSupport::JSON.decode(response.body)) if response.status == HTTP::Status::OK
    raise "Error fetching person: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.search(first_name, last_name, birth_date, phone)
    query = {
      :firstName => first_name,
      :lastName => last_name,
      :birthDate => birth_date,
      :phone => phone
    }
    response = HTTPClient.new.get(Person.service_url, query)
    return ActiveSupport::JSON.decode(response.body).map { |props| Person.new(props) } if response.status == HTTP::Status::OK
    raise "Error searching person: #{response.body}" if response.status != HTTP::Status::OK
  end

  def self.match(person)
    query = {
      :firstName => person.firstName,
      :lastName => person.lastName,
      :birthDate => person.birthDate,
      :phone => person.phone
    }
    response = HTTPClient.new.get(Person.service_url, query)
    return ActiveSupport::JSON.decode(response.body).map { |props| Person.new(props) } if response.status == HTTP::Status::OK
    raise "Error matching person: #{response.body}" if response.status != HTTP::Status::OK
  end

  private
  def self.service_url
    return "#{Discovery.lookup("identity", "general")}/v1/person"
  end

end