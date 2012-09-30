/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

const strEmpty = "?";
var decs = ".";
var secV = {sT:strEmpty, dP:-1, dA:-1, PW:-1, PZ:-1, nA:0, nEmp:0};
var minV = {sT:strEmpty, Pp:-1, Wu:-1, du:0, Ww:-1, dw:0, Po:-1, Pr:-1, Pw:-1, Pps:-1, Wus:-1, dus:0, Wws:-1, dws:0, Pos:-1, Prs:-1, Pws:-1};
var iniV = {sT:strEmpty, ds:".", nD:"|", ui:1000, np:360, na:5, ns:10, ne:6, mdP:60};
var oldS = {sT:strEmpty};
var oldM = {sT:strEmpty};
var isStart = true;

var namTabH = "#hTable";
var namTabS = "#sTable";

function getS2F(s){
//    if (decs != iniV.ds) return(+(s.replace(iniV.ds, decs))); else return(0+s); // parseFloat(s));
    if (decs != iniV.ds) return(parseFloat(s.replace(iniV.ds, decs))); else return(parseFloat(s));
}

function getS2I(s){
//    var i1=parseInt(s);    var i2=parseInt(s,10);    var i3=+s;    
    return(parseInt(s, 10));
}
