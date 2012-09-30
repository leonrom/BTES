var sChart = {plt:null, res:[], nA:-1};

function initPlot(){
    sChart.res = [];
    var n = iniV.np+iniV.na;
    for (var i = 0; i < n; ++i) sChart.res.push([i, 0]);  // null]);		
    
    sChart.plt = $.plot($("#sChart"), [sChart.res], {
        series: {shadowSize: 0 }, 
        yaxis: {min: -iniV.mdP, max: iniV.mdP, position: "right"},
        xaxis: {show: false },
        grid: {borderWidth: 0, borderColor: "#ffffff"}
    });    
}
function drawPlot(){
    sChart.plt.setData([sChart.res]);
    sChart.plt.draw();
}

function setNewPlot(j){   
    var i = 0;
    if ((j % iniV.ns) == 0){
        for (i = 1; i < iniV.np; ++i) sChart.res[i-1][1] = sChart.res[i][1];
    }
    var n = iniV.np+iniV.na;
    var y = secV.dA;
    for (i=(iniV.np-1); i < n; i++) sChart.res[i][1] = y;     
    sChart.nA = secV.nA;
    drawPlot();
}

function setOldPlot(dat){    
    var i = 0;
    var L = dat.length;
    var nA = getS2I(dat[0]);
    var shd = nA - secV.nA;
    var last = iniV.np - shd - 1;
    while ((last > 0) && (i < L)){
        if (last < iniV.np) {
            sChart.res[last][1] = getS2F(dat[i++]);
        }
        last--;
    }
    drawPlot();
}