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
    else
      flash[:error] = "Invalid credentials were provided."
      redirect_to login_show_path
    end
  end

  def create
    @email = params[:email]
    @firstName = params[:firstName]
    @lastName = params[:lastName]

    if verify_recaptcha
      account = Account.new({
        :email => @email,
        :password => params[:password],
        :firstName => @firstName,
        :lastName => @lastName,
      })

      if account.save
        set_current_account(account)
        redirect_to root_path
      else
        flash[:error] = "An account with this email address already exists."
        render :action => 'show'
      end
    else
      flash[:error] = "Incorrect Captcha code entered."
      render :action => 'show'
    end
  end

  def destroy
    set_current_account(nil)
    redirect_to login_show_path
  end

end
