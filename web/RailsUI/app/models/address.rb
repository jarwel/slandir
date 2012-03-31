class Address
  attr_accessor :street, :city, :state, :zip

  def initialize(hash)
      hash.each { |k, v| self.send("#{k}=".to_sym, v) if defined?("#{k}=".to_sym) }
  end

  def to_hash
    return {
      :street => street,
      :city => city,
      :state => state,
      :zip => zip
    }
  end
end