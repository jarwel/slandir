module Discovery

  def self.lookup(type, pool)
    services = lookup_all(type, pool)
    return services[rand(services.size)]
  end

  def self.lookup_all(type, pool)
    response = HTTPClient.new.get("http://localhost:8000/v1/service/#{type}/#{pool}")
    list = ActiveSupport::JSON.decode(response.body)["services"]

    services = Array.new
    list.each{ |i|
      services.push(i["properties"]["http"])
    }
    return services
  end

end