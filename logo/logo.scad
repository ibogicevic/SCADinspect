$fn=100;
module scadInspectLogo() {
  // S
  color("orange")
  scale(30,5,5)
  text("S");
  // CAD
  color("dimgray") {
  translate([265, 200])
  scale(9,5,5)
  text("C");
  translate([270, 100])
  scale(9,5,5)
  text("A");
  translate([265, 0])
  scale(9,5,5)
  text("D");
  // inspect
  translate([20, -80])
  scale(8,5,5)
  text("inspect");
  }
 }
 
 scadInspectLogo();