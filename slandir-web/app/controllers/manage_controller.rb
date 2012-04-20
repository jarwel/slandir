class ManageController < ApplicationController
  def index
    @account = current_account
    @grievances = Grievance.fetchByAccount(@account.id)
  end

  def destroy
    Grievance.remove(params[:grievanceId])
    redirect_to manage_index_path
  end

end
