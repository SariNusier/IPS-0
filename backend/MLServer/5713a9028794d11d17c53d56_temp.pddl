(define (problem simplemuseum)
(:domain museum)
(:objects
    visitor - person
    e0 e1 - exhibit
)
(:init
(want-to-see e0)
(want-to-see e1)
(path e0 e1) (path e1 e0)
(= (time-to-walk e0 e1) 0)
(= (time-to-walk e1 e0) 0)
(= (time-to-see e0) 13)
(= (time-to-see e1) 6)
(at visitor e0)
(= (seen) 0)
(= (excitement e0) 1)
(= (excitement e1) 1)
(open)
(at 10000 (not (open)))
)
(:goal (and
;(visited e0)
;(visited e1)
)
)
(:metric maximize (seen))
)
