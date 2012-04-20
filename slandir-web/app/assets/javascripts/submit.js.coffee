# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://jashkenas.github.com/coffee-script/

$("#person-form").ready ->
  $("#person-form").validate()

$("#grievance-form").ready ->
  $("#grievance-form").validate(
    errorPlacement: (error, element) ->
      if element.attr("id") is "description"
        error.insertBefore(element)
      else
        error.insertAfter(element)
  )