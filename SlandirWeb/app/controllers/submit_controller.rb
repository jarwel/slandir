class SubmitController < ApplicationController

  def new

  end

  def match
    name =  params[:name].split(" ")

    first_name = name.first if name.size > 0
    last_name = name.last if name.size > 1
    middle_name = name.second if name.size > 2
    birthDate = params[:birth_date]
    phone = params[:phone].delete("^0-9")

    new_person = Person.new({
      :firstName => first_name,
      :lastName => last_name,
      :middleName => middle_name,
      :gender => params[:gender],
      :birthDate => params[:birth_date].to_s,
      :phone => phone,
      :email => params[:email],
      :address => {
        :street => params[:street],
        :city => params[:city],
        :state => params[:state],
        :zip => params[:zip]
      }
    })

    session[:person_info] = new_person
    @persons = Person.match(new_person)
  end

  def create
    personId = params[:personId]
    if !personId.nil?
      session[:person_info].id = personId
    end
    @redirect_url = params[:redirect_url]
  end

  def show
    person_info = session[:person_info]

    if person_info.id.nil?
      person_info.save
    else
      person_info.update
    end

    grievance = Grievance.new({
      :accountId => current_account.id,
      :personId => person_info.id,
      :author => params[:author],
      :description => params[:description],
      :created => Time.new.to_s
    })

    grievance.save
    session[:person_info] = nil
  end


end
