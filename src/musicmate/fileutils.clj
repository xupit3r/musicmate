(ns musicmatch.fileutils)

;;;; contains utility functions related to file operations ;;;;

;; below are some naive strategies for leveraging
;; the underlying Java file utilities.  there is
;; probably a better way to do this...

;; creates a reader for the file
(defn getIstream [filename]
  (java.io.FileInputStream. (java.io.File. filename)))

;; reads one line at a time
;; prints each line
(defn readlines [istream]
  (let [line (.readLine istream)]
    (when (not (nil? line))
      (println line)
      (recur istream))))

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
(defn readX [istream x y]
  (.skip istream y)
  (rxHlp istream x '() 0))
