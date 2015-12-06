//(function($) {

    $(function () {

        $('#paramNum').keyup(function () {
            var m_param = $("#m_param").val();
            var t_param = $("#t_param").val();
            var sigma_param = $("#sigma_param").val();
            var w_param = $("#w_param").val();
            var val = $("#paramNum").val();
            if ($.isNumeric(val) && val != 0 && $.isNumeric(m_param) && $.isNumeric(t_param) && $.isNumeric(w_param) && $.isNumeric(sigma_param)
                && m_param && w_param && t_param && sigma_param) {
                    $("#generate").attr('disabled', false);
                    $("#params").show();
                    var tbody = $("#params").find('tbody');
                    tbody.children().remove();
                    for (var i = 0; i < val; ++i) {
                        tbody.append("<tr>");
                        var idA = "a_" + i;
                        var idT = "t_" + i;
                        tbody.append("<td><input type='text' class='param_value' id=" + idA+ " value='0'></td>")
                            .append("<td><input type='text' class='param_value' id=" + idT+ " value='0'></td>");
                        tbody.append("</tr>");
                    }

            } else {
                $("#params").hide();
                $("#generate").attr('disabled', true);
                $("#analyze").attr('disabled', true);
                $("#xi_2").hide();
            }
        });

        $("#generate").on('click', function() {
            var data=0;
            var m_param = $("#m_param").val();
            var t_param = $("#t_param").val();
            var w_param = $("#w_param").val();
            var sigma_param = $("#sigma_param").val();
            var n = $("#paramNum").val();
            var params = $(".param_value");
            var a_i= [n];
            var t_i = [n];
            for(var i = 0; i < params.length; ++i){
                var param = params[i];
                var val = $(param).val();
                var id = $(param).attr('id');
                var splitted_id = id.split('_');
                var index = splitted_id[1];
                var array_type = splitted_id[0];
                if (array_type === 'a') {
                    a_i[index] = val;

                } else if (array_type === 't') {
                    t_i[index] = val;
                }
            }

            $.ajax({
                url: "/generate",
                type: "POST",
                //contentType : "application/json",
                data: {
                    'M' : m_param,
                    'T' : t_param,
                    'W' : w_param,
                    'sigma' : sigma_param,
                    'n' : n,
                    'a' : a_i.toString(),
                    'tau' : t_i.toString()
                },
                success: (function (res) {
                    $("#analyze").attr('disabled', false);
                    var y = [1,5,15,10];
                    chart(y);
                }),
                error: function () {
                    $("#analyze").attr('disabled', true);
                    $("#xi_2").hide();
                }
            });
        });

        $("#analyze").on('click', function() {
            alert("analyzation");
            var y = [1,5,15,10];
            var z = [1,6,16,10];
            chart(y, z);
            $("#xi_2").show();
        });

    });

function chart(ex, th) {
    var limit = ex.length;
    var data = [];
    var T = $("#t_param").val();
    var W = $("#w_param").val();
    var N = 0;
    if($.isNumeric(T) && $.isNumeric(W)) {
        N = T / W +1;
    }
    var x = [];
    for(var i = 0; i < N; ++i) {
        if(i==0) {
            x[i] = 0;
        } else {
            x[i] = Number( x[i - 1]) + Number(W);
        }
    }

    if(th) {
        var dataSeriesTh = {type: "line"};
        var dataPointsTh = [];
        for (var i = 0; i < limit; i += 1) {
            dataPointsTh.push({
                x: x[i],
                y: th[i]
            });
        }
        dataSeriesTh.dataPoints = dataPointsTh;
        data.push(dataSeriesTh);
    }

    var dataSeriesEx = {type: "line"};
    var dataPointsEx = [];
    for (var i = 0; i < limit; i += 1) {
        dataPointsEx.push({
            x: x[i],
            y: ex[i]
        });
    }
    dataSeriesEx.dataPoints = dataPointsEx;
    data.push(dataSeriesEx);

    //Better to construct options first and then pass it as a parameter
    var options = {
        zoomEnabled: true,
        animationEnabled: true,
        title: {
            text: "Кривая затухания"
        },
        axisX: {
            labelAngle: 30
        },
        axisY: {
            includeZero: false
        },
        data: data
    };

    $("#chartContainer").CanvasJSChart(options);
}

//})(jQuery);