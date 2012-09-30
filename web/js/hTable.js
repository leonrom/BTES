var hou = ["00-01","01-02","02-03","03-04","04-05","05-06","06-07","07-08","08-09","09-10","10-11","11-12",
"12-13","13-14","14-15","15-16","16-17","17-18","18-19","19-20","20-21","21-22","22-23","23-24"];

function initTab(){
    jQuery(namTabH).jqGrid({
        datatype: "local",
        height: 560,
	width: 400,
        gridHeight: 8,
        colNames:['Час','План', 'Wucte', 'P-W u','W wps','P-W w','P u/oik','P u/reg','P w/oik','Hou'],
        colModel:[        
        { name:'hou', index:'hou', width:40,  align:"center",sorttype:"text"},
        { name:'pln', index:'pln', width:45,  align:"right", sorttype:"int" },
        { name:'uct', index:'uct', width:45,  align:"right", sorttype:"int" },
        { name:'pwu', index:'pwu', width:45,  align:"right", sorttype:"int" },
        { name:'wps', index:'wps', width:46,  align:"right", sorttype:"int" },		
        { name:'pww', index:'pww', width:46,  align:"right", sorttype:"int" },
        { name:'uoi', index:'uoi', width:46,  align:"right", sorttype:"int" },	        
        { name:'ure', index:'ure', width:46,  align:"right", sorttype:"int" },
        { name:'woi', index:'woi', width:46,  align:"right", sorttype:"int" },
        { name:'ho2', index:'woi', width:40,  align:"right", sorttype:"int" }
        ],
        multiselect: false,
        scrollOffset:0,        
        footerrow : true, 
        userDataOnFooter : true, 
        altRows : true,
        caption: ''
    });
        
    var mydat, grid = $(namTabH);
    grid.jqGrid('clearGridData');
    for(var i=0; i< 24; i++){	
        mydat = {hou:hou[i], ho2:hou[i]};		
        grid.jqGrid('addRowData', i+1, mydat);
        grid.setRowData ( i, true, {height: 4} );
    }	        
/*    
    var ids = grid.getDataIDs();
    for (i = 0; i < ids.length; i++) {
        grid.setRowData ( ids[i], false, {height: 8} );
    }
*/    
//    grid.setGridHeight(552,true);
//    grid.setGridWidth(400,true);
}

function fillTabm(h){
    var grid = $(namTabH);
    var i = h + 0;
    grid.setCell(i, 'pln', minV.Pp);
    grid.setCell(i, 'uct', minV.Wu);
    grid.setCell(i, 'pwu', minV.du);
    grid.setCell(i, 'wps', minV.Ww);
    grid.setCell(i, 'pww', minV.dw);
    grid.setCell(i, 'uoi', minV.Po);
    grid.setCell(i, 'ure', minV.Pr);
    grid.setCell(i, 'woi', minV.Pw);   
    var sum = grid.jqGrid('getCol', 'pln', false, 'sum');
    grid.jqGrid('footerData','set', {hou: 'сум.:'        
        , pln:  minV.Pps
        , uct:  minV.Wus
        , pwu:  minV.dus
        , wps:  minV.Wws
        , pww:  minV.dws
        , uoi:  minV.Pos
        , ure:  minV.Prs
        , woi:  minV.Pws
    });
}

function fillOldm(dat){
    var L = dat.length;
    var j = 0, h=1, a;
    for (var i = 0; i < L; i++){
        if (++j > 6) {
            j = 1;
            h++;
        }
        if (j == 1){
            minV.Pp = null;  minV.Wu = null;  minV.Ww = null;  minV.Po = null;  minV.Pr = null;  minV.Pw = null; minV.du = null; minV.dw = null;
        }
        if (dat[j] != iniV.nD){
            a = getS2F(dat[i]); 
            switch (j){
                case 1: minV.Pp = a; break;
                case 2: minV.Wu = a; break; 
                case 3: minV.Ww = a; break; 
                case 4: minV.Po = a; break; 
                case 5: minV.Pr = a; break; 
                case 6: minV.Pw = a; break;
            }
            minV.du = minV.Wu - minV.Pp;
            minV.dw = minV.Ww - minV.Pp;
            minV.sT = h;
            
            fillTabm(h);
        }
    }
}