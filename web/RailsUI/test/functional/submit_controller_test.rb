require 'test_helper'

class SubmitControllerTest < ActionController::TestCase
  def setup
    session[:account] = Account.new({
      :id => UUIDTools::UUID.timestamp_create.to_s,
      :email => "someone@somewhere.com",
      :password => "P@ssw0rd",
      :firstName => "John",
      :lastName => "Doe",
    })
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should get match" do
    get :match
    assert_response :success
  end

  test "should get create" do
    get :create
    assert_response :success
  end

  test "should get show" do
    get :show
    assert_response :success
  end

end
