(ns musicmatch.fileutils)

;;;; contains utility functions related to file operations ;;;;

;; below are some naive strategies for leveraging
;; the underlying Java file utilities.  there is
;; probably a better way to do this...

;; creates a reader for the file
(defn get-reader [filename]
  (java.io.BufferedReader. (java.io.FileReader. filename)))

;; reads one character at a time
;; prints each character
(defn bf_read [reader]
  (let [x (.read reader)]
    (when (pos? x)
      (print (Character. x))
      (recur reader))))


;; reads one line at a time
;; prints each line
(defn readlines [reader]
  (let [line (.readLine reader)]
    (when (not (nil? line))
      (println line)
      (recur reader))))

