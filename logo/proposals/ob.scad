echo(version=version());

r=65; //Radius of circle

difference() {
    cylinder (h = 1, r=5, center = true, $fn=100);
    union(){
        color("red") cylinder(h = 2, r=4, center = true, $fn=100);
        for(i=[0:20:360]) rotate([0,0,i]) color ("red") square([0.5,17], center=true);
    }
}

difference(){
    cylinder (h=1, r=3, center=true, $fn=100);
    union(){
        for(i=[0:20:360]) rotate([0,0,i]) color ("red") square([0.5,17], center=true);
    }
}