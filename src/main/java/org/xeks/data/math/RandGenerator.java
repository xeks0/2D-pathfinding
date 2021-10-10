package org.xeks.data.math;

import lombok.Getter;
import lombok.Setter;
import org.xeks.Vector;

public class RandGenerator {


    public RandGenerator(int seed, int rangeMin_, int rangeMax_) {
        staticConstructor(this, seed, rangeMin_, rangeMax_);
    }

    public static void staticConstructor(RandGenerator _this, int seed, int rangeMin_, int rangeMax_) {
        _this._originalSeed = _this._currSeed = seed;
        _this.rangeMin = rangeMin_;
        _this.rangeMax = rangeMax_;
        _this._numIter = 0;
    }


    @Getter
    @Setter
    public int rangeMin;

    @Getter
    @Setter
    public int rangeMax;

    @Getter
    @Setter
    public int _originalSeed;

    @Getter
    @Setter
    public int _currSeed;

    @Getter
    @Setter
    public int _rangeMin;

    @Getter
    @Setter
    public int _rangeMax;

    @Getter
    @Setter
    public int _numIter;

    @Getter
    @Setter
    public java.lang.String _tempString;


    public void reset() {
        this._currSeed = this._originalSeed;
        this._numIter = 0;
    }


    public int next() {
        double _floatSeed = (this._currSeed * 1.0);
        this._tempString = String.valueOf(_floatSeed * _floatSeed);
        while ((this._tempString.length() < 8)) {
            this._tempString = ("0" + this._tempString);
        }
        this._currSeed = Integer.parseInt(_tempString.substring(1, 5));

        int res = ((int) (java.lang.Math.round((this.rangeMin + ((((double) (this._currSeed)) / 99999) * ((this.rangeMax - this.rangeMin)))))));
        if ((this._currSeed == 0)) {
            this._currSeed = (this._originalSeed + this._numIter);
        }
        this._numIter++;
        if ((this._numIter == 200)) {
            this.reset();
        }
        return res;
    }


    public int nextInRange(int rangeMin, int rangeMax) {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        return this.next();
    }


    public <T> void shuffle(Vector<T> array) {
        int currIdx = array.length;
        while ((currIdx > 0)) {
            int rndIdx = this.nextInRange(0, (currIdx - 1));
            --currIdx;
            T tmp = array.get(currIdx);
            array.set(currIdx, array.get(rndIdx));
            array.set(rndIdx, tmp);
        }

    }
}
