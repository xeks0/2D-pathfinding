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
        double _floatSeed = (double) this._currSeed * 1.0D;
        for (this._tempString = Integer.toString((int) (_floatSeed * _floatSeed)).toString(); this._tempString.length() < 8; this._tempString = "0" + this._tempString) {
        }
        this._currSeed = Integer.parseInt(substr(this._tempString, 1, 5));
        int res = (int) Math.round((double) this.rangeMin + (double) this._currSeed / 99999.0D * (double) (this.rangeMax - this.rangeMin));
        if (this._currSeed == 0) {
            this._currSeed = this._originalSeed + this._numIter;
        }
        ++this._numIter;
        if (this._numIter == 200) {
            this.reset();
        }
        return res;
    }

    public static String substr(String me, int pos, Object len) {
        int meLen = me.length();
        int targetLen = meLen;
        if (len != null) {
            targetLen = Integer.parseInt(len.toString());
            if (targetLen == 0) {
                return "";
            }
            if (pos != 0 && targetLen < 0) {
                return "";
            }
        }

        if (pos < 0) {
            pos += meLen;
            if (pos < 0) {
                pos = 0;
            }
        } else if (targetLen < 0) {
            targetLen = meLen + targetLen - pos;
        }

        if (pos + targetLen > meLen) {
            targetLen = meLen - pos;
        }

        return pos >= 0 && targetLen > 0 ? me.substring(pos, pos + targetLen) : "";
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
