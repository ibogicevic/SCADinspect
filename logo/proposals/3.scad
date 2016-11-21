echo(version=version());

r=65; //Radius of circle

translate([-16.5,-7,0]){
    text("inspect",8);
}
translate([-16,2.5,0]){
    text("SCAD",5);
}

translate([20,-20,0]){
    rotate([90,0,45]) cylinder (h=50,r=2,$fn=100);
}

translate([0,0,0]){

    difference() {
        cylinder (h = 5, r=30, center = true, $fn=100);
        union(){
            cylinder(h = 6, r=25, center = true, $fn=100);
            for(i=[0:10:360]) rotate([0,0,i]) cube([0.5,100,10],center=true);
        }
    }

    difference(){
        cylinder (h=1, r=23, center=true, $fn=100);
        union(){
            cylinder (h=2, r=20, center=true, $fn=100);
            for(i=[0:15:360]) rotate([0,0,i-8]) square([0.5,60], center=true);
        }
    }
}