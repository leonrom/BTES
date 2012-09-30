function initAll(){    
    initTab();
    initTabs();
    initPlot();
    initPChart();
}        

$(function () {
    //	editableGrid.loadXML("data/grid.xml");
    if  (isStart){
        isStart = false;
        dwr.engine.setActiveReverseAjax(true);
        initAll();
        
        decs = (1.1).toLocaleString().substring(1, 2);
        Clock.connect();
        doCicle();
    }
});

function decodeOldS() {
//    var str = $('#archSecond')[0].innerText;
    var str = $('#archSecond')[0].childNodes[0].wholeText;
    var dat = str.split(" ");	
    if (dat.length > 2) {
        if (oldS.sT != dat[0]){
            oldS.sT = dat[0]; 
            setOldPlot(dat);
        }	
    }
}

function decodeOldM() {
//    var str = $('#archMinute')[0].innerText;
    var str = $('#archMinute')[0].childNodes[0].wholeText;
    var dat = str.split(" ");	
    if (dat.length > 2) {
        if (oldM.sT != dat[0]){
            oldM.sT = dat[0]; 
            fillOldm(dat);
        }	
    }
}

function decodeI() {
//    var str = $('#initParams')[0].innerText;
    var str = $('#initParams')[0].childNodes[0].wholeText;
    var dat = str.split(" ");	
    if (dat.length > 7) {
        if (iniV.sT != dat[0]){
            iniV.sT = dat[0]; 
            iniV.ds = dat[1]; 
            iniV.nD = dat[2]; 
            iniV.ui = getS2I(dat[3]); 
            iniV.np = getS2I(dat[4]); 
            iniV.na = getS2I(dat[5]); 
            iniV.ne = getS2I(dat[6]); 
            iniV.mdP = getS2I(dat[7]); 
        }	
    }
}

function decodeM() {
//    var str = $('#currMinute')[0].innerText;
    var str = $('#currMinute')[0].childNodes[0].wholeText;
    var dat = str.split(" ");	
    if (dat.length > 6) {
        if (minV.sT != dat[0]){
            minV.sT = dat[0]; 
            minV.Pp = getS2F(dat[1]); 
            minV.Wu = getS2F(dat[2]); 
            minV.Ww = getS2F(dat[3]); 
            minV.Po = getS2F(dat[4]); 
            minV.Pr = getS2F(dat[5]); 
            minV.Pw = getS2F(dat[6]); 
            minV.du = minV.Wu - minV.Pp;
            minV.dw = minV.Ww - minV.Pp;
            
            minV.Pps = getS2F(dat[7]); 
            minV.Wus = getS2F(dat[8]); 
            minV.Wws = getS2F(dat[9]); 
            minV.Pos = getS2F(dat[10]); 
            minV.Prs = getS2F(dat[11]); 
            minV.Pws = getS2F(dat[12]); 
            minV.dus = minV.Wus - minV.Pps;
            minV.dws = minV.Wws - minV.Pps;            
            
            var ss = minV.sT.split(":", 1);
            var i = getS2I(ss[0]);        
            fillTabm(i+1);            
            
            decodeI();
            decodeOldS();
            decodeOldM();
        }	
    }
}

function decodeStr() {
//    var str = $('#currSecond')[0].innerText;
    var str = $('#currSecond')[0].childNodes[0].wholeText;
    var dat = str.split(" ");	
    if (dat.length > 4) {
        if ((secV.sT != dat[0]) && (dat[0].length > 6)){
            secV.sT = dat[0]; 
            secV.dP = getS2F(dat[1]);   
            secV.dA = getS2F(dat[2]); 
            secV.PW = getS2F(dat[3]); 
            secV.PZ = getS2F(dat[4]); 
            secV.nA = getS2I(dat[5]); 
            
            fillTabs();
            fillPChart();
                
            decodeM();
                
            if (iniV.sT == strEmpty) decodeI();
            if (oldS.sT == strEmpty) decodeOldS();
            if (oldM.sT == strEmpty) decodeOldM();            
            
            var ss = secV.sT.split(":", 3);
            var j = getS2I(ss[2]);            
            setNewPlot(j);
            secV.nEmp = 0;
        }	
    }
    else{
        if (secV.neEmp++ > iniV.ne){
            Clock.connect();
            secV.nEmp = 0;
        }
    }
    return (true);
}

function doCicle(){

    function update() {
        decodeStr();
        setTimeout(update, iniV.ui);
    }

    update();
}

var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
    var thd = $("thead:first", grid.hdiv)[0];
//    $("tr th:eq(" + iColumn + ")", thd).attr("title", text);
    thd.rows[0].cells[0].textContent = text;
};