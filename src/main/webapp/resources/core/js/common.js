//(function($) {
$(function () {
    $('#fillParams').on('click', function () {
        var m_param = $("#m_param").val();
        var N_param = $("#N_param").val();
        var sigma_param = $("#sigma_param").val();
        var w_param = $("#w_param").val();
        var val = $("#paramNum").val();
        if ($.isNumeric(val) && val != 0) {
            //$("#generate").attr('disabled', false);
            $("#params_ex").show();
            var tbody = $("#params_ex").find('tbody');
            tbody.children().remove();
            for (var i = 0; i < val; ++i) {
                tbody.append("");
                var idA = "a_" + i;
                var idT = "t_" + i;
                var textP = "p" + (i + 1) + ":";
                //tau
                var textT = '&#964;' + (i + 1) + ":";
                tbody.append("<tr><td>" + textP + "</td>" +
                    "<td><input type='text' class='param_value' id=" + idA + " value='0'></td>" +
                    "<td>&nbsp</td><td>" + textT + "</td>" +
                    "<td><input type='text' class='param_value' id=" + idT + " value='0'></td>" +
                    "</tr>");
            }

        }
    });

    $('#fillParamsTh').on('click', function () {
        var val = $("#paramNumTh").val();
        if ($.isNumeric(val) && val != 0) {
            //$("#generate").attr('disabled', false);
            $("#params_th").show();
            var tbody = $("#params_th").find('tbody');
            tbody.children().remove();
            for (var i = 0; i < val; ++i) {
                tbody.append("");
                var idA = "a_" + i;
                var idT = "t_" + i;
                var textP = "p" + (i + 1) + ":";
                var textT = '&#964;' + (i + 1) + ":";
                tbody.append("<tr><td>" + textP + "</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idA + "_min' value='0.01'></td>" +
                    "<td><=</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idA + "_ap' value='0.1'></td>" +
                    "<td><=</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idA + "_max'></td>" +
                    "<td>&nbsp</td><td>" + textT + "</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idT + "_min' value='0.01'></td>" +
                    "<td><=</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idT + "_ap' value='0.1'></td>" +
                    "<td><=</td>" +
                    "<td><input type='text' class='param_value_th' id='" + idT + "_max'></td>" +
                    "</tr>");
            }

        }
    });

    $("#generate").on('click', function () {
        generate();
    });

    $("#analyze").on('click', function () {
        analyze();
    });

    //tabbable
    $(".tabbable").find(".tab").hide();
    $(".tabbable").find(".tab").first().show();
    $(".tabbable").find(".tabs li").first().find("a").addClass("active");
    $(".tabbable").find(".tabs").find("a").click(function () {
        tab = $(this).attr("href");
        $(".tabbable").find(".tab").hide();
        $(".tabbable").find(".tabs").find("a").removeClass("active");
        $(tab).show();
        $(this).addClass("active");
    });

});

function analyze() {
    var data = 0;
    var n = $("#paramNumTh").val();
    var params = $(".param_value_th");
    var epsilon_param = $("#epsilon_param").val();
    var maxIteration_param = $("#maxIteration_param").val();
    var a_i=[],t_i=[], a_min_i=[], t_min_i=[],a_max_i=[], t_max_i=[];
    for (var i = 0; i < params.length; ++i) {
        var param = params[i];
        var val = $(param).val();
        var id = $(param).attr('id');
        var splitted_id = id.split('_');
        var index = splitted_id[1];
        var array_type = splitted_id[0];
        var value_type = splitted_id[2];
        if (array_type === 'a') {
            if(value_type === 'min') {
                a_min_i[index] = val;
            } else if(value_type === 'max') {
                a_max_i[index] = val;
            } else {
                a_i[index] = val;    
            }
            
        } else if (array_type === 't') {
            if(value_type === 'min') {
                t_min_i[index] = val;
            } else if(value_type === 'max') {
                t_max_i[index] = val;
            } else {
                t_i[index] = val;
            }
        }
    }

    // $.ajax({
    //     type: "POST",
    //     url: "/getExperimentalData",
    //     dataType: 'json',
    //     success: (function (res) {
    //         var x = res;
            $.ajax({
                type: "POST",
                url: "/analyze",
                data: {
                    'n': n,
                    'eps': epsilon_param,
                    'maxIt': maxIteration_param,
                    'a': a_i.toString(),
                    'tau': t_i.toString(),
                    'a_min': a_min_i.toString(),
                    'tau_min': t_min_i.toString(),
                    'a_max': a_max_i.toString(),
                    'tau_max': t_max_i.toString()
                },
                dataType: 'json',
                success: (function (res) {
                    var y = res.thResult;
                    var x = res.expResult;
                    var chi = res.chiSq;
                    var lambda = res.lambda;
                    var eps = res.eps;
                    var eq = res.eqResult;
                    var autocor = res.autoCor;
                    chart(x,"exp", 'experimental',eq, "eq","equipment", y, "theor", 'theorethical');
                    chartAutoCor(autocor, "autocor", "auto correlation");
                    $("#xi_2_val").text(chi);
                    $("#xi_2").show();
                    $("#eps_val").text(eps);
                    $("#eps").show();
                    $("#lambda_val").text(lambda);
                    $("#lambda").show();
                    var tau = res.tauResult;
                    var p = res.pefResult;
                    for(var i = 0; i < tau.length; ++i) {
                        $("#t_" + i + "_ap").val(tau[i]);
                        $("#a_" + i + "_ap").val(p[i]);
                    }


                }),
                error: function () {
                    //$("#analyze").attr('disabled', true);
                    $("#xi_2").hide();
                    $("#eps").hide();
                    $("#lambda").hide();

                }
            });

    //     }),
    //     error: function () {
    //         $("#analyze").attr('disabled', true);
    //         $("#xi_2").hide();
    //     }
    // });

}

function generate() {

    var data = 0;
    var m_param = $("#m_param").val();
    var N_param = $("#N_param").val();
    var w_param = $("#w_param").val();
    var sigma_param = $("#sigma_param").val();
    var n = $("#paramNum").val();
    var noise = $("#noise").is(":checked");
    var norm = $("#norm").is(":checked");
    var params = $(".param_value");
    var a_i = [n];
    var t_i = [n];
    for (var i = 0; i < params.length; ++i) {
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

    if($.isNumeric(m_param) && $.isNumeric(N_param)
        && $.isNumeric(w_param) && $.isNumeric(sigma_param) && $.isNumeric(n)) {
        $("#warn").hide();
        $("#xi_2").hide();
        $("#eps").hide();
        $("#lambda").hide();
        $.ajax({
            type: "POST",
            url: "/generate",
            data: {
                'M': m_param,
                "numOfPockets": N_param,
                'W': w_param,
                'sigma': sigma_param,
                'n': n,
                'a': a_i.toString(),
                'tau': t_i.toString(),
                'normalization': norm,
                'noise':noise
            },
            dataType: 'json',
            success: (function (genResult) {
                $.ajax({
                    type: "POST",
                    url: "/getEquipmentResponse",
                    dataType: 'json',
                    success: (function (equipmentResponse) {
                        chart(genResult, "exp", "experimental", equipmentResponse, "eqRS", "equipment response");
                    })
                });
                $("#analyze").attr('disabled', false);
                // chart(genResult, "exp", "experimental");
            }),
            error: function () {
                $("#analyze").attr('disabled', true);
            }
        });
    } else {
        $("#warn").show();
    }

}
function chartAutoCor(autocor, s, s2) {

    var data = [];
    var limit = autocor.length;
    var dataSeriesAC = {type: "line"};
    var dataPointsAC = [];
    var x =[];
    x[0] = 0;
    for (var i = 1; i < autocor.length; ++i) {
        x[i] = i;
    }
    for (var i = 0; i < limit; i += 1) {
        dataPointsAC.push({
            x: x[i],
            y: autocor[i]
        });
    }
    dataSeriesAC.dataPoints = dataPointsAC;
    dataSeriesAC.showInLegend = 'true';
    dataSeriesAC.name = s;
    dataSeriesAC.legendText = s2;
    dataSeriesAC.color = "blue";
    data.push(dataSeriesAC);
    var options = {
        zoomEnabled: true,
        animationEnabled: true,
        title: {
            text: "График автокорреляции"
        },
        axisX: {
            labelAngle: 30
        },
        axisY: {
            includeZero: false
        },
        data: data

    };

    $("#chartContainer_autocor").CanvasJSChart(options);
}
function chart(ex, exName, exText, eq, eqName, eqText, th, thName, thText) {
    var limit = ex.length;
    var data = [];
    var N = $("#N_param").val();
    var W = $("#w_param").val();
    var x = [];
    if ($.isNumeric(N) && $.isNumeric(W)) {
        x[0] = 0;
        for (var i = 1; i < N; ++i) {
            x[i] = Number(x[i - 1]) + Number(W);
        }
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
    dataSeriesEx.showInLegend = 'true';
    dataSeriesEx.name = exName;
    dataSeriesEx.legendText = exText;
    dataSeriesEx.color = "blue";
    data.push(dataSeriesEx);

    if (eq) {
        var dataSeriesEq = {type: "line"};
        var dataPointsEq = [];
        for (var i = 0; i < limit; i += 1) {
            dataPointsEq.push({
                x: x[i],
                y: eq[i]
            });
        }
        dataSeriesEq.dataPoints = dataPointsEq;
        dataSeriesEq.showInLegend = 'true';
        dataSeriesEq.name = eqName;
        dataSeriesEq.legendText = eqText;
        dataSeriesEq.color = "green";
        data.push(dataSeriesEq);
    }

    if (th) {
        var dataSeriesTh = {type: "line"};
        var dataPointsTh = [];
        for (var i = 0; i < limit; i += 1) {
            dataPointsTh.push({
                x: x[i],
                y: th[i]
            });
        }
        dataSeriesTh.dataPoints = dataPointsTh;
        dataSeriesTh.showInLegend = 'true';
        dataSeriesTh.name = thName;
        dataSeriesTh.legendText = thText;
        dataSeriesTh.color = "red";
        data.push(dataSeriesTh);
    }
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