class LoginController < ApplicationController
  skip_before_filter :check_current_account

  def show

  end

  def new
    email = params[:newEmail]
    password = params[:newPassword]

    account = Account.authenticate(email, password)
    if !account.nil?
      set_current_account(account)
      redirect_to root_path
    end
  end

  def create
    if verify_recaptcha
      account = Account.new({
        :email => params[:email],
        :password => params[:password],
        :firstName => params[:firstName],
        :lastName => params[:lastName],
      })

      if account.save
        set_current_account(account)
        redirect_to root_path
      else
        flash[:error] = "An account with that email address already exists."
        redirect_to login_show_path
      end
    else
      flash[:error] = "Incorrect Captcha code entered."
      redirect_to :back
    end
  end

  def destroy
    set_current_account(nil)
    redirect_to login_show_path
  end

end
