
// logo.scad - Basic example of module, top-level variable and $fn usage

Logo(50);

/**
 * @title Ano![]\{}{|':"~,./<>?~#$%^&*()ther Module
 * @price 1123445~EUR
 * @material test; abcd
 *
 */
module Logo(size=50, $fn=100) {
    // Temporary variables
    hole = size/2;
    cylinderHeight = size * 1.25;

    // One positive object (sphere) and three negative objects (cylinders)
    difference() {
        sphere(d=size);

        cylinder(d=hole, h=cylinderHeight, center=true);
        // The '#' operator highlights the object
        #rotate([90, 0, 0]) cylinder(d=hole, h=cylinderHeight, center=true);
        rotate([0, 90, 0]) cylinder(d=hole, h=cylinderHeight, center=true);
    }
}
/**
 * @title Another Module2
 * @price 1145~USD
 * @author
 * @material test; def
 *
 */
echo(version=version());
// Written by Clifford Wolf <clifford@clifford.at> and Marius
// Kintel <marius@kintel.net>
//
// To the extent possible under law, the author(s) have dedicated all
// copyright and related and neighboring rights to this software to the
// public domain worldwide. This software is distributed without any
// warranty.
//
// You should have received a copy of the CC0 Public Domain
// Dedication along with this software.
// If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.

