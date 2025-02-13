(define (problem simplemuseum)
(:domain museum)
(:objects
    visitor - person
    door e2 e3 e4 e5 e6 - exhibit    ;; five real exhibits and the door
)
(:init

    (want-to-see e2)
    (want-to-see e3)
    (want-to-see e4)
    (want-to-see e5)    
    (want-to-see e6)
    
    (path door e2) (path e2 door)    ;; these define the layout of the museum
    (path e2 e3) (path e3 e2)
    (path e2 e4) (path e4 e2)
    (path e3 e5) (path e5 e3)
    (path e4 e5) (path e5 e4)
    (path e4 e6) (path e6 e4)
    
    (= (time-to-walk door e2) 2)
    (= (time-to-walk e2 door) 2)
    
    (= (time-to-walk e2 e3) 5)  ;; how long to walk from e2 to e3
    (= (time-to-walk e3 e2) 5)  ;; we can also walk back, it takes the same amount of time
    
    (= (time-to-walk e2 e4) 5)
    (= (time-to-walk e4 e2) 5)
    
    (= (time-to-walk e4 e5) 5)
    (= (time-to-walk e5 e4) 5)
    
    (= (time-to-walk e3 e5) 5)
    (= (time-to-walk e5 e3) 5)
    
    (= (time-to-walk e4 e6) 1)
    (= (time-to-walk e4 e6) 1)
    
    (= (time-to-see e2) 5)  ;; how long to see exhibit e2
    (= (time-to-see e3) 25) ;; how long to see exhibit e3, etc.
    (= (time-to-see e4) 20)
    (= (time-to-see e5) 15)
    (= (time-to-see e6) 30)
    
    (at visitor door)
    
    (= (seen) 0)
    (= (excitement e2) 5) ;; how much they want to see e2
    (= (excitement e3) 5) ;; how much they want to see e3
    (= (excitement e4) 3)
    (= (excitement e5) 1)
    (= (excitement e6) 8) ;; they really want to see this one
    
    (open) ;; the museum starts by being open
    
    (at 70 (not (open)))  ;; change 70 to the deadline
)
(:goal (and
    ;(visited e2)
    ;(visited e3)
    ;(visited e4)
    ;(visited e5)
    ;(visited e6)
)
)

(:metric maximize (seen))

)
