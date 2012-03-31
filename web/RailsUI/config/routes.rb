RailsUI::Application.routes.draw do

  get "login/show"
  post "login/create"
  post "login/new"
  delete "login/destroy"

  get "search/show"
  get "search/home"
  post "search/index"

  get "submit/new"
  post "submit/create"
  post "submit/match"
  post "submit/show"

  get "manage/index"
  delete "manage/destroy"

  root :to => 'search#home'

end
