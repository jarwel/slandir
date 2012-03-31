module ApplicationHelper

  def current_account
    return session[:account]
  end

  def format_last_initial(firstName, lastName)
    return "#{firstName} #{lastName.chars.first}"
  end

  def format_age(date)
    now = Date.current
    age = now.year - date.year
    age -= 1 if now.yday < date.yday
    return age
  end

  def format_birth_date(date)
    return date.strftime("%B %d, %Y")
  end

  def format_phone(phone)
    return phone.insert(3, '-').insert(7, '-')
  end

  def format_city_state_zip(city, state, zip = nil)
    value = String.new
    value << city if !city.blank?
    value << ', ' if !city.blank? && !state.blank?
    value << state if !state.blank?
    value << zip.to_s if !zip.blank?
    return value
  end

end
