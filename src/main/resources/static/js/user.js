var deleteButtonName = "delete";
var editButtonName = "edit";

$(document).ready(
    function () {
      $('button').click(function (event) {
        buttonClickHandler(event, $(this));
      });
    }
);

function buttonClickHandler(event, button) {
  console.log("buttonClickHandler " + button.val());
  //stop submit the form, we will post it manually.
  event.preventDefault();
  var requestDetails = {}
  requestDetails["user"] = button.val();
  if (button.hasClass("delete-button")) {
    requestDetails["url"] = "/admin/user/delete";
  }
  if (button.hasClass("edit-button")) {
    requestDetails["url"] = "/admin/user/data";
  }
  if (button.hasClass("save-button")) {
    requestDetails["url"] = "/admin/user/save";
    var modalJsonData = getModalJson();
    requestDetails["user"] = JSON.stringify(modalJsonData);
  }
  if (button.hasClass("add-button")) {
    requestDetails["url"] = "/admin/user/data";
    requestDetails["user"] = "-1";
    // var modalJsonData = getModalJson();
    // requestDetails["user"] = JSON.stringify(modalJsonData);
    // return;
  }
  fireAjaxSubmit(requestDetails);
}

function getModalJson() {
  console.log("getModalJson");
  var modalJsonData = {};
  $.each($(".modal-content input"), function (i, item) {
    modalJsonData[item.name] = item.value;
  });
  $.each($(".modal-content select"), function (i, item) {
    modalJsonData[item.name] = item.value;
  });
  return modalJsonData;
}

function modalHandler(button) {
  console.log("modalHandler");
  if (button.hasClass("modal-handler")) {
    $("#userModal").modal();
  }
}

function fillUserModal(result) {
  console.log("fillUserModal");
  $('#modalUserId').val(result.id);
  $('#firstName').val(result.firstName);
  $('#lastName').val(result.lastName);
  $('#roleSelect option').remove();
  $.each(result.roles, function (i, role) {
    $('#roleSelect').append($('<option>').text(role).val(role));
  });
  $("#roleSelect option[value=" + result.role + "]").attr('selected', 'true');
  $('#locationSelect option').remove();
  $.each(result.locations, function (i, location) {
    $('#locationSelect').append($('<option>').text(location).val(location));
  });
  $("#locationSelect option[value=" + result.location + "]").attr('selected',
      'true');
  $('#photo').val(result.photo);
  $('#login').val(result.login);
  $('#password').val(result.password);
}

function handleSuccessAjax(requestDetails, result) {
  console.log("handleSuccessAjax - " + requestDetails["url"]);
  if (requestDetails["url"] == "/admin/user/delete") {
    cleanTable();
    fillTable(result);
  }
  if (requestDetails["url"] == "/admin/user/data") {
    fillUserModal(result);
  }
  if (requestDetails["url"] == "/admin/user/save") {
    cleanTable();
    fillTable(result);
  }
}

function fillTable(result) {
  console.log("fillTable");
  $.each(result, function (i, item) {
        $('<tr>').append(
            $('<td>').text(item.id).css("display", "none"),
            $('<td>').text(item.firstName),
            $('<td>').text(item.lastName),
            $('<td>').text(item.role),
            $('<td>').text(item.location),
            $('<td>').text(item.photo),
            $('<td>').text(item.login),
            $('<td>').text(item.password),
            $('<td>').append(
                $('<button>').text(editButtonName).addClass(
                    'btn btn-primary edit-button modal-handler').attr(
                    "data-toggle", "modal").attr("data-target", "#myModal").attr(
                    "type", "button").attr('value', item.id),
                $('<button>').text(deleteButtonName).addClass(
                    'btn btn-danger delete-button')
                .attr('value', item.id)
            )
        ).appendTo("table.table.table-striped tbody");
        //was buttonClickHandler
      }
  );
  $('button').unbind('click');
  $('button').click(function (event) {
    buttonClickHandler(event, $(this));
    modalHandler($(this));
  });
}

function cleanTable() {
  console.log("cleanTable");
  $("table.table.table-striped tbody tr").remove();
}

function fireAjaxSubmit(requestDetails) {
  $.ajax({
        type: "POST",
        contentType: "application/json",
        url: requestDetails["url"],
        data: requestDetails["user"],
        dataType: 'json',
        success: function (result) {
          console.log("success");
          handleSuccessAjax(requestDetails, result);
        },
        error: function (e) {
          console.log("ERROR : ", e);
        }
      }
  );
}




