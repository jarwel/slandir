# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://jashkenas.github.com/coffee-script/

$ ->
  $("#navigation-bar li a").each ->
    if ($(this).attr("href").toLowerCase() == location.pathname.toLowerCase())
      $(this).parent("li").addClass("active")

$("#search-form").ready ->
  $("#search-form").validate(
     errorPlacement: (error, element) ->
       error.insertBefore(element);
  )
