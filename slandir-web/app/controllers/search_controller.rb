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
    @state = params[:state]

    @persons = Person.search(@first_name, @last_name, @state)
  end

end
