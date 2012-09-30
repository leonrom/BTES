var option;
var resu = [];
var resw = [];
var plotarea, jdt;

function fillPChart() {
    var i, i3, du, dw;
    var grid = $(namTabH); 
    
    for(i=0; i< 24; i++){
        du = grid.getCell(24-i, 'pwu');
        dw = grid.getCell(24-i, 'pww');
        i3=i*3;
        resu[i3] = [0, i3];
        resu[i3+1] = [du, i3+1];
        resu[i3+2] = [0, i3+2];
        resw[i3] = [0, i3];
        resw[i3+1] = [0, i3+1];
        resw[i3+2] = [dw, i3+2];
    }	

    $.plot( plotarea , jdt, option);
    return(0);
}

function initPChart() { 
    var i, i3, tick_label = [];
    //	[[0, ""], [1, ""], [2, ""], [3, ""], [4, ""], [5, ""], [6, ""], [7, ""], [8, ""], [9, ""], [10, ""], [11, ""], [12, ""], [13, ""], [14, ""], [15, ""], [16, ""], [17, ""], [18, ""], [19, ""], [20, ""], [21, ""], [22, ""], [23, ""]];           
	
    for(i=0; i< 24; i++){
        i3=i*3;
        resu.push([0, i3]);
        resu.push([1, i3+1]);
        resu.push([0, i3+2]);
		
        resw.push([0, i3]);
        resw.push([0, i3+1]);
        resw.push([-2, i3+2]);
		
        tick_label.push([i3, ""]);
        tick_label.push([i3+1, ""]);
        tick_label.push([i3+2, ""]);
    }	
	
    option = {
        //        border: '15px solid #000000',
        series: {
            stack: true,
            lines: {show: false, steps: false},
            bars: {show: true, align: 'top', horizontal: true}
        },
        yaxis: {ticks: tick_label},
        xaxis: {min: -10,   max: 10,  position: "top" },
        grid:{borderWidth: 0, borderColor: "#ffffff"}
    };
	
    jdt = [
    {
        "data": resu, 
        "label": "",
        bars: {show: true, fill: true, lineWidth: 1, order: 1, fillColor:  "#a6d7de"},
        color: "#AA4643"
    },
    {
        "data": resw, 
        "label": "",
        bars: {barHeight: 2, show: true, fill: true, lineWidth: 1, order: 2, fillColor:  "#e1f4ae"},
        color: "#89A54E"
    }
    ];
    plotarea = $("#hChart");
    plotarea.css("height", "599px");
    plotarea.css("width", "100px");
    return(0);
}

