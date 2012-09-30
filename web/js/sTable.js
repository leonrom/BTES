var spln = [-405,-384, -20, -409];

function initTabs(){
    jQuery(namTabS).jqGrid({
	datatype: "local",
	height: 600,
	gridHeight: 25,
   	colNames:['Plan', 'P ucte', 'dP cep', 'P zes'],
   	colModel:[
   		{name:'wpln',index:'pln', width:65, align:"right", sorttype:"int"},
   		{name:'wuct',index:'uct', width:65, align:"right", sorttype:"int"},
   		{name:'dpln',index:'pwu', width:65, align:"right", sorttype:"int"},
   		{name:'pzes',index:'wps', width:66, align:"right", sorttype:"int"}
   	],
   	multiselect: false,
        scrollOffset:0,
   	caption: " ."
    });
    var mydat;	
    var grid = $(namTabS);
    grid.jqGrid('clearGridData');
    mydat = {wpln:spln[0],wuct:spln[1],dpln:spln[2],pzes:spln[3]};		
    grid.jqGrid('addRowData', 1, mydat);	
    
    var ids = grid.getDataIDs();
    grid.setRowData ( ids[0], false, {height: 10} );
    grid.setGridHeight('auto');
}

function fillTabs(){
    var grid = $(namTabS);    

    grid.setCell(1, 'wuct', secV.PW);
    grid.setCell(1, 'dpln', secV.dA);
    grid.setCell(1, 'pzes', secV.PZ);
    
    grid.setCaption(secV.sT);

}