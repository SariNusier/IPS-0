(define (problem simplemuseum)
(:domain museum)
(:objects
    visitor - person
    e0 - exhibit
)
(:init
(want-to-see e0)
(= (time-to-see e0) 0)
(at visitor e0)
(= (seen) 0)
(= (excitement e0) 1)
(open)
(at 10000 (not (open)))
)
(:goal (and
;(visited e0)
)
)
(:metric maximize (seen))
)
