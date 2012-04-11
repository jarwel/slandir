class ApplicationController < ActionController::Base
  protect_from_forgery
  before_filter :check_current_account

  def check_current_account
    if current_account.nil?
      redirect_to login_show_path
    end
  end

  def current_account
    return session[:account]
  end

  def set_current_account(account)
    session[:account] = account
  end

end
