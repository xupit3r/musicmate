(ns musicmatch.fileutils)

;;;; contains utility functions related to file operations ;;;;

;; below are some naive strategies for leveraging
;; the underlying Java file utilities.  there is
;; probably a better way to do this...

;; returns an input stream
;; for a file
(defn getIstream [filename]
  (java.io.FileInputStream. (java.io.File. filename)))

;; helper function for readlines
(defn rlHlp [istream acc]
  (let [line (.readLine istream)]
    (if (not (nil? line))
      (recur istream (conj acc line))
      (reverse acc))))

;; reads each line of a
;; file into a list
(defn readlines [filename]
  (rlHlp (getIstream filename) '()))

;; helper function for to
;; accumulate list of bytes
(defn rxHlp [istream x acc pos]
  (let [byte (.read istream)]
    (if (and (pos? byte) (< pos x))
      (recur istream x
             (conj acc byte)
             (inc pos))
      (reverse acc))))

;; reads x bytes
;; with offset y
(defn readX [filename x y]
  (let [istream (getIstream filename)]
    (.skip istream y)
    (rxHlp istream x '() 0)))

;;;; Stuff to deal with bits and bytes ;;;;

(defn getbit [byte bit]
  (if (> (bit-and
          (bit-shift-right byte bit) 1)
         0) 1 0))

;; bitme helper
(defn bmHlp [byte bit lst]
  (let [rslt (getbit byte bit)]
    (if (< bit 8)
      (recur byte (inc bit) (conj lst rslt))
      (reverse lst))))

;; turn a byte list into a bit
;; list. note the least significant
;; bit will be on the left and not
;; the right
(defn bitme [byte]
  (bmHlp byte 0 '()))  

;; takes a list of bytes and
;; returns a list of bits that
;; represent the bytes
(defn getbits [bytes]
  (mapcat bitme bytes))