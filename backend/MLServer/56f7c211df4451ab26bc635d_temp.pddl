(define (problem simplemuseum)
(:domain museum)
(:objects
    visitor - person
    e0 e1 e2 - exhibit
)
(:init
(want-to-see e0)
(want-to-see e1)
(want-to-see e2)
(= (time-to-see e0) 1231)
(= (time-to-see e1) 7758)
(= (time-to-see e2) 0)
(at visitor e0)
(= (seen) 0)
(= (excitement e0) 1)
(= (excitement e1) 1)
(= (excitement e2) 1)
(open)
(at 10000 (not (open)))
)
(:goal (and
;(visited e0)
;(visited e1)
;(visited e2)
)
)
(:metric maximize (seen))
)
