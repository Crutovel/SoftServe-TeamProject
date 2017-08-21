var deleteButtonName = "delete";
var editButtonName = "edit";

$(document).ready(
    function () {
      $('button').click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        var student_id = $(this).val();
        fireAjaxSubmit(student_id);
      });
    }
);

function fillTable(result) {
  $.each(result, function (i, item) {
        $('<tr>').append(
            $('<td>').text(item.id).css("display", "none"),
            $('<td>').text(item.firstName),
            $('<td>').text(item.lastName),
            // $('<td>').text(item.role),
            // $('<td>').text(item.location),
            // $('<td>').text(item.photo),
            // $('<td>').text(item.login),
            // $('<td>').text(item.password),
            $('<td>').append(
                $('<button>').text(editButtonName).addClass('btn btn-primary'),
                $('<button>').text(deleteButtonName).addClass('btn btn-danger')
                .attr('value', item.id)
            )
        ).appendTo("table.table.table-striped tbody");
        $('button').click(function (event) {
          //stop submit the form, we will post it manually.
          event.preventDefault();
          var student_id = $(this).val();
          fireAjaxSubmit(student_id);
        });
      }
  );
}

function cleanTable() {
  $("table.table.table-striped tbody tr").remove();
}

function fireAjaxSubmit(studentId) {
  $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/admin/student/delete",
        data: studentId,
        dataType: 'json',
        success: function (result) {
          console.log(result);
          cleanTable();
          fillTable(result);
        },
        error: function (e) {
          console.log("ERROR : ", e);
        }
      }
  );
}
