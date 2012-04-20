require 'test_helper'

class SearchControllerTest < ActionController::TestCase
  def setup
    session[:account] = Account.new({
      :id => UUIDTools::UUID.timestamp_create.to_s,
      :email => "someone@somewhere.com",
      :password => "P@ssw0rd",
      :firstName => "John",
      :lastName => "Doe",
    })
  end

  test "should get show" do
    get :show
    assert_response :success
  end

  test "should get index" do
    get :index
    assert_response :success
  end

  test "should get home" do
    get :home
    assert_response :success
  end

end
