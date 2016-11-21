//set Viewport
$vpr = [47, 0, 95];
$vpt = [24, 7, 15];
$vpd = 446;

//magifying glass
rotate([0, 30, 0])
translate([0, 0, 30])
union() {
	difference() {
		cylinder(h=10, r=40);
		translate([0, 0, -5])
			cylinder(h=20, r=30); 
	}
	translate([0, 35, 5])
		rotate([-90, 0, 0]) 
		cylinder(h=80, r=5);
}

//floor
color("blue")
	translate([-100, -100, -30])
	cube([200, 200, 10]);

//name text
color("red") 
	scale([0.8, 0.8, 1])
	//rotate([20, -30, 155])
	rotate([0, 0, 155])
	translate([-10, 10, -20])
	linear_extrude(5)
	text("SCADinspect");