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
  event.preventDefault();
  var requestDetails = {}
  requestDetails["group"] = button.val();
  if (button.hasClass("delete-button")) {
    requestDetails["url"] = "/admin/group/delete";
  }
  if (button.hasClass("edit-button")) {
    requestDetails["url"] = "/admin/group/data";
  }
  if (button.hasClass("save-button")) {
    requestDetails["url"] = "/admin/group/save";
    var modalJsonData = getModalJson();
    requestDetails["group"] = JSON.stringify(modalJsonData);
  }
  if (button.hasClass("add-button")) {
    requestDetails["url"] = "/admin/group/data";
    requestDetails["group"] = "-1";
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
    $("#groupModal").modal();
  }
}

function fillUserModal(result) {
  console.log("fillGroupModal");
  $('#modalGroupId').val(result.id);
  $('#name').val(result.name);

  $.each(result.locations, function (i, location) {
    $('#locationSelect').append($('<option>').text(location).val(location));
  });
  $("#locationSelect option[value=" + result.location + "]").attr('selected', 'true');

  $('#startDate').val(result.startDate);
  $('#finishDate').val(result.finishDate);

  $.each(result.statuses, function (i, status) {
    $('#statusSelect').append($('<option>').text(status).val(status));
  });
  $("#statusSelect option[value=" + result.status + "]").attr('selected', 'true');

  $.each(result.specializations, function (i, specialization) {
    $('#specializationSelect').append($('<option>').text(specialization).val(specialization));
  });
  $("#specializationSelect option[value=" + result.specialization + "]").attr('selected', 'true');

  $.each(result.budgetOwners, function (i, budgetOwner) {
      $('#budgetOwnerSelect').append($('<option>').text(budgetOwner).val(budgetOwner));
    });
    $("#budgetOwnerSelect option[value=" + result.budgetOwner + "]").attr('selected', 'true');

  $('#deleted').val(result.deleted);
}

function handleSuccessAjax(requestDetails, result) {
  console.log("handleSuccessAjax - " + requestDetails["url"]);
  if (requestDetails["url"] == "/admin/group/delete") {
    cleanTable();
    fillTable(result);
  }
  if (requestDetails["url"] == "/admin/group/data") {
    fillUserModal(result);
  }
  if (requestDetails["url"] == "/admin/group/save") {
    cleanTable();
    fillTable(result);
  }
}

function fillTable(result) {
  console.log("fillTable");
  $.each(result, function (i, item) {
        $('<tr>').append(
            $('<td>').text(item.id).css("display", "none"),
            $('<td>').text(item.name),
            $('<td>').text(item.location),
            $('<td>').text(item.startDate),
            $('<td>').text(item.finishDate),
            $('<td>').text(item.status),
            $('<td>').text(item.specialization),
            $('<td>').text(item.budgetOwner),
            $('<td>').text(item.deleted),
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
        data: requestDetails["group"],
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