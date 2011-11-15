(ns musicmatch.fileutils)

;;;; contains utility functions related to file operations ;;;;

;; below are some naive strategies for leveraging
;; the underlying Java file utilities.  there is
;; probably a better way to do this...

;; creates a reader for the file
(defn get_reader [filename]
  (java.io.BufferedReader. (java.io.FileReader. filename)))

;; reads one line at a time
;; prints each line
(defn readlines [reader]
  (let [line (.readLine reader)]
    (when (not (nil? line))
      (println line)
      (recur reader))))

;; helper function for to
;; accumulate list of bytes
(defn rxHlp [reader x acc pos]
  (if (< pos x)
    (recur reader x
           (conj acc (.read reader))
           (inc pos))
    (reverse acc)))

;; reads x bytes
;; with offset y
(defn readXBytes [reader x y]
  (.skip reader y)
  (rxHlp reader x '() 0))
