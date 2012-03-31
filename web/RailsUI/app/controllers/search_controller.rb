class SearchController < ApplicationController

  def home
  end

  def show
    @person = Person.get(params[:personId])
    @grievances = Grievance.fetchByPerson(params[:personId])
  end

  def index
    @first_name = params[:first_name]
    @last_name = params[:last_name]
    @birth_date = params[:birth_date]
    @phone = params[:phone]

    @persons = Person.search(@first_name, @last_name, @birth_date, @phone)
  end

end
