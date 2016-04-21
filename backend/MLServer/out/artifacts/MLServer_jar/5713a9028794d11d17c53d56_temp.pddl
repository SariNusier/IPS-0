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
(path e0 e1) (path e1 e0)
(= (time-to-walk e0 e1) 0)
(= (time-to-walk e1 e0) 0)
(path e0 e2) (path e2 e0)
(= (time-to-walk e0 e2) 0)
(= (time-to-walk e2 e0) 0)
(path e1 e2) (path e2 e1)
(= (time-to-walk e1 e2) 0)
(= (time-to-walk e2 e1) 0)
(= (time-to-see e0) 16)
(= (time-to-see e1) 12)
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
