translate([0,0,0]) {
    translate([0,0,0]) green() t("SCAD", 40, ":style=Bold");
    translate([150,0,0]) black() t("inspect", 42, ":style=Bold");
}

//Module to create the 3D Text
module t(t, s = 18, style = "") {
  rotate([90, 0, 0])
    linear_extrude(height = 1)
      text(t, size = s, font = str("Liberation Sans", style), $fn = 16);
}

//Colors
module green() color([81/255, 142/255, 4/255]) children();
module black() color([0, 0, 0]) children();