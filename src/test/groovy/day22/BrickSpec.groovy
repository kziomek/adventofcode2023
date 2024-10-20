package day22

import advent.day22.Brick
import advent.day22.Point
import spock.lang.Specification

class BrickSpec extends Specification {

    def "cubes intersectsXY"() {
        given:
        Brick b1 = new Brick(new Point(0, 0, 0), new Point(0, 0, 1))
        Brick b2 = new Brick(new Point(0, 0, 0), new Point(0, 0, 1))

        expect:
        b1.intersectsXY(b2)
    }

    def "should intersectXY where y is fixed"() {
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result

        // do not overlap
        new Brick(new Point(0, 0, 0), new Point(2, 0, 0)) | new Brick(new Point(3, 0, 1), new Point(3, 0, 1)) | false
        new Brick(new Point(4, 0, 0), new Point(5, 0, 0)) | new Brick(new Point(3, 0, 1), new Point(3, 0, 1)) | false

        //fully overlap
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(2, 0, 1), new Point(2, 0, 1)) | true
        new Brick(new Point(2, 0, 1), new Point(2, 0, 1)) | new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | true

        //fully overlap - left adjustment
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(0, 0, 1), new Point(2, 0, 1)) | true
        new Brick(new Point(0, 0, 1), new Point(2, 0, 1)) | new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | true

        //fully overlap - right adjustment
        new Brick(new Point(2, 0, 1), new Point(3, 0, 1)) | new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | true
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(2, 0, 1), new Point(3, 0, 1)) | true

        //fully overlap - both side adjustment
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(0, 0, 1), new Point(3, 0, 1)) | true


        // not fully overlap
        new Brick(new Point(2, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(3, 0, 1), new Point(4, 0, 1)) | true
        new Brick(new Point(2, 0, 0), new Point(4, 0, 0)) | new Brick(new Point(3, 0, 1), new Point(5, 0, 1)) | true
        new Brick(new Point(1, 0, 1), new Point(4, 0, 1)) | new Brick(new Point(2, 0, 0), new Point(5, 0, 0)) | true
        new Brick(new Point(1, 0, 1), new Point(4, 0, 1)) | new Brick(new Point(4, 0, 0), new Point(5, 0, 0)) | true
    }

    def "should not intersectXY by x where y is not fixed"() {
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result

        // do not overlap
        new Brick(new Point(0, 0, 0), new Point(2, 0, 0)) | new Brick(new Point(3, 2, 1), new Point(3, 2, 1)) | false
        new Brick(new Point(4, 0, 0), new Point(5, 0, 0)) | new Brick(new Point(3, 2, 1), new Point(3, 2, 1)) | false

        //fully overlap
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(2, 2, 1), new Point(2, 2, 1)) | false
        new Brick(new Point(2, 0, 1), new Point(2, 0, 1)) | new Brick(new Point(0, 2, 0), new Point(3, 2, 0)) | false

        //fully overlap - left adjustment
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(0, 2, 1), new Point(2, 2, 1)) | false
        new Brick(new Point(0, 0, 1), new Point(2, 0, 1)) | new Brick(new Point(0, 2, 0), new Point(3, 2, 0)) | false

        //fully overlap - right adjustment
        new Brick(new Point(2, 0, 1), new Point(3, 0, 1)) | new Brick(new Point(0, 2, 0), new Point(3, 2, 0)) | false
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(2, 2, 1), new Point(3, 2, 1)) | false

        //fully overlap - both side adjustment
        new Brick(new Point(0, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(0, 2, 1), new Point(3, 2, 1)) | false


        // not fully overlap
        new Brick(new Point(2, 0, 0), new Point(3, 0, 0)) | new Brick(new Point(3, 2, 1), new Point(4, 2, 1)) | false
        new Brick(new Point(2, 0, 0), new Point(4, 0, 0)) | new Brick(new Point(3, 2, 1), new Point(5, 2, 1)) | false
        new Brick(new Point(1, 0, 1), new Point(4, 0, 1)) | new Brick(new Point(2, 2, 0), new Point(5, 2, 0)) | false
        new Brick(new Point(1, 0, 1), new Point(4, 0, 1)) | new Brick(new Point(4, 2, 0), new Point(5, 2, 0)) | false
    }

    def "should intersectXY where x is fixed"() {
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result
        // do not overlap
        new Brick(new Point(0, 0, 0), new Point(0, 2, 0)) | new Brick(new Point(0, 3, 1), new Point(0, 3, 1)) | false
        new Brick(new Point(0, 4, 0), new Point(0, 5, 0)) | new Brick(new Point(0, 3, 1), new Point(0, 3, 1)) | false

        // fully overlap
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | true
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | true

        //fully overlap  - aligned both
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(0, 2, 1), new Point(0, 3, 1)) | true

        // fully overlap - aligned left
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(0, 2, 1), new Point(0, 4, 1)) | true
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 1, 0), new Point(0, 3, 0)) | true

        // fully overlap - aligned right
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(0, 1, 1), new Point(0, 3, 1)) | true
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 2, 0), new Point(0, 4, 0)) | true

        // not fully overlap
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(0, 3, 1), new Point(0, 4, 1)) | true
        new Brick(new Point(0, 2, 0), new Point(0, 4, 0)) | new Brick(new Point(0, 3, 1), new Point(0, 5, 1)) | true
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 2, 0), new Point(0, 5, 0)) | true
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 4, 0), new Point(0, 5, 0)) | true
    }

    def "should noty intersectXY by y where x is not fixed"() {
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result
        // do not overlap
        new Brick(new Point(0, 0, 0), new Point(0, 2, 0)) | new Brick(new Point(1, 3, 1), new Point(1, 3, 1)) | false
        new Brick(new Point(0, 4, 0), new Point(0, 5, 0)) | new Brick(new Point(1, 3, 1), new Point(1, 3, 1)) | false

        // fully overlap
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(1, 1, 1), new Point(1, 4, 1)) | false
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(1, 2, 0), new Point(1, 3, 0)) | false

        //fully overlap  - aligned both
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(1, 2, 1), new Point(1, 3, 1)) | false

        // fully overlap - aligned left
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(1, 2, 1), new Point(1, 4, 1)) | false
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(1, 1, 0), new Point(1, 3, 0)) | false

        // fully overlap - aligned right
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(1, 1, 1), new Point(1, 3, 1)) | false
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(1, 2, 0), new Point(1, 4, 0)) | false

        // not fully overlap
        new Brick(new Point(0, 2, 0), new Point(0, 3, 0)) | new Brick(new Point(1, 3, 1), new Point(1, 4, 1)) | false
        new Brick(new Point(0, 2, 0), new Point(0, 4, 0)) | new Brick(new Point(1, 3, 1), new Point(1, 5, 1)) | false
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(1, 2, 0), new Point(1, 5, 0)) | false
        new Brick(new Point(0, 1, 1), new Point(0, 4, 1)) | new Brick(new Point(1, 4, 0), new Point(1, 5, 0)) | false
    }

    def "intersect"() {
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result
        // do not overlap
//        new Brick(new Point(1, 0, 1), new Point(1, 2, 1)) | new Brick(new Point(0, 0, 2), new Point(2, 0, 2)) | true

        new Brick(new Point(1, 0, 1), new Point(1, 2, 1)) | new Brick(new Point(0, 2, 2), new Point(2, 2, 2)) | true

        new Brick(new Point(2, 3, 1), new Point(2, 5, 1)) | new Brick(new Point(0, 2, 2), new Point(4, 2, 2)) | false
        new Brick(new Point(2, 3, 1), new Point(2, 5, 1)) | new Brick(new Point(0, 6, 2), new Point(4, 6, 2)) | false
        new Brick(new Point(2, 3, 1), new Point(2, 5, 1)) | new Brick(new Point(0, 4, 2), new Point(4, 4, 2)) | true

        new Brick(new Point(2, 3, 1), new Point(2, 5, 1)) | new Brick(new Point(0, 4, 2), new Point(1, 4, 2)) | false
        new Brick(new Point(2, 3, 1), new Point(2, 5, 1)) | new Brick(new Point(3, 4, 2), new Point(4, 4, 2)) | false


        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(1, 1, 2), new Point(1, 3, 2)) | false
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(5, 1, 2), new Point(5, 3, 2)) | false

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 1, 2), new Point(2, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 2, 2), new Point(2, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 2, 2), new Point(2, 3, 2)) | true

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(4, 1, 2), new Point(4, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(4, 2, 2), new Point(4, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(4, 2, 2), new Point(4, 3, 2)) | true

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(3, 1, 2), new Point(3, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(3, 2, 2), new Point(3, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(3, 2, 2), new Point(3, 3, 2)) | true

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 1, 2), new Point(2, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 2, 2), new Point(2, 2, 2)) | true
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(2, 2, 2), new Point(2, 3, 2)) | true

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(4, 1, 2), new Point(4, 1, 2)) | false
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(4, 3, 2), new Point(4, 3, 2)) | false

        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(3, 1, 2), new Point(3, 1, 2)) | false
        new Brick(new Point(2, 2, 1), new Point(4, 2, 1)) | new Brick(new Point(3, 3, 2), new Point(3, 3, 2)) | false
    }

    def "other random test"(){
        expect:
        brick1.intersectsXY(brick2) == result

        where:
        brick1                                            | brick2                                            | result
        // do not overlap
//        new Brick(new Point(1, 0, 1), new Point(1, 2, 1)) | new Brick(new Point(0, 0, 2), new Point(2, 0, 2)) | true

        new Brick(new Point(0, 2, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 4, 2), new Point(0, 7, 2)) | true
        new Brick(new Point(0, 2, 1), new Point(0, 4, 1)) | new Brick(new Point(0, 2, 2), new Point(2, 2, 2)) | true

    }
}
