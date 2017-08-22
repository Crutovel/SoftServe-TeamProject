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
  requestDetails["student"] = button.val();
  if (button.hasClass("delete-button")) {
    requestDetails["url"] = "/admin/student/delete";
  }
  if (button.hasClass("edit-button")) {
    requestDetails["url"] = "/admin/student/data";
  }
  if (button.hasClass("save-button")) {
    requestDetails["url"] = "/admin/student/save";
    var modalJsonData = getModalJson();
    requestDetails["student"] = JSON.stringify(modalJsonData);
  }
  if (button.hasClass("add-button")) {
    requestDetails["url"] = "/admin/student/data";
    requestDetails["student"] = "-1";
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
    $("#studentModal").modal();
  }
}

function fillUserModal(result) {
  console.log("fillStudentModal");
  $('#modalStudentId').val(result.id);
  $('#firstName').val(result.firstName);
  $('#lastName').val(result.lastName);

  $.each(result.groups, function (i, group) {
    $('#groupSelect').append($('<option>').text(group).val(group));
  });
  $("#groupSelect option[value=" + result.group + "]").attr('selected', 'true');

  $.each(result.englishLevels, function (i, englishLevel) {
    $('#englishLevelSelect').append(
        $('<option>').text(englishLevel).val(englishLevel));
  });
  $("#englishLevelSelect option[value=" + result.englishLevel + "]").attr(
      'selected', 'true');

  $('#incomingTest').val(result.incomingTest);
  $('#entryScore').val(result.entryScore);

  $.each(result.experts, function (i, expert) {
    $('#expertSelect').append($('<option>').text(expert).val(expert));
  });
  $("#expertSelect option[value=" + result.expert + "]").attr('selected',
      'true');
}

function handleSuccessAjax(requestDetails, result) {
  console.log("handleSuccessAjax - " + requestDetails["url"]);
  if (requestDetails["url"] == "/admin/student/delete") {
    cleanTable();
    fillTable(result);
  }
  if (requestDetails["url"] == "/admin/student/data") {
    fillUserModal(result);
  }
  if (requestDetails["url"] == "/admin/student/save") {
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
            $('<td>').text(item.group),
            $('<td>').text(item.englishLevel),
            $('<td>').text(item.incomingTest),
            $('<td>').text(item.entryScore),
            $('<td>').text(item.expert),
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
        data: requestDetails["student"],
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




