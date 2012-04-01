class Account
  attr_accessor :id, :email, :password, :firstName, :lastName

  def initialize(hash)
    hash.each { |k, v| self.send("#{k}=".to_sym, v) if defined?("#{k}=".to_sym) }
  end

  def save
    self.id = UUIDTools::UUID.timestamp_create.to_s if self.id.nil?
    hash = {
      :id => id,
      :email => email,
      :password => password,
      :firstName => firstName,
      :lastName => lastName
    }
    response = HTTPClient.new.post(Account.service_url, hash.to_json, {"Content-Type" => "application/json"})

    return false if response.status == HTTP::Status::BAD_REQUEST
    return true if response.status == HTTP::Status::OK

    raise "Error saving account: #{response.body}" if response.status != HTTP::Status::OK || response.status != HTTP::Status::BAD_REQUEST
  end

  def self.authenticate(email, password)
    response = HTTPClient.new.get(Account.service_url, { :email => email, :password => password })
    return Account.new(ActiveSupport::JSON.decode(response.body)) if response.status == HTTP::Status::OK
    raise "Error authenticating account: #{response.body}" if response.status != HTTP::Status::OK
  end

  private
  def self.service_url
    return "#{Discovery.lookup("account", "general")}/v1/account"
  end

end