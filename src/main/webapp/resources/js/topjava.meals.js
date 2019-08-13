const mealAjaxUrl = 'ajax/profile/meals/';
function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

function initDateTimePickers() {
    $.datetimepicker.setLocale('ru');
    $('#startDate, #endDate').datetimepicker({
        format: 'Y-m-d',
        onShow:function actual(){
            this.setOptions({
                maxDate:$('#endDate').val() ? $('#endDate').val() : false,
                minDate:$('#startDate').val() ? $('#startDate').val() : false
            })
        },
        timepicker: false

    });
    $('#startTime, #endTime').datetimepicker({
        format: 'H:i',
        onShow:function actual(){
            this.setOptions({
                maxDate:$('#endTime').val() ? $('#endTime').val() : false,
                minDate:$('#startTime').val() ? $('#startTime').val() : false
            })
        },
        datepicker: false
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
}

    function convertDate() {
        let newDate = form.find('#dateTime').val().replace(' ', 'T');
        form.find('#dateTime').val(newDate);
}



$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": (data, type, row) => {
                       if (type === 'display')
                          return data.substring(0, 16).replace('T', ' ');
                       return data;
                      }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "createdRow": function(row,data,index) {
              row.classList.add(data.excess ? 'blue' : 'green');
            },
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
    initDateTimePickers();
});