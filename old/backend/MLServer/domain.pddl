(define (domain museum)
(:requirements :typing :durative-actions :fluents)
(:types
    exhibit person - object
)
(:predicates
    (at ?p - person ?e - exhibit)
    (path ?e1 ?e2 - exhibit)
    (want-to-see ?e - exhibit)
    (visited ?e - exhibit)
    (open)
)
(:functions
    (time-to-walk ?e1 ?e2 - exhibit)
    (time-to-see ?e - exhibit)
    (excitement ?e - exhibit)
    (seen)
)

(:durative-action walk
  :parameters
   (?p - person
    ?e1 ?e2 - exhibit)
  :duration (= ?duration (time-to-walk ?e1 ?e2))
  :condition
   (and (over all (path ?e1 ?e2)) (at start (at ?p ?e1)) (over all (open)))
  :effect
   (and (at start (not (at ?p ?e1))) (at end (at ?p ?e2))))

(:durative-action view
  :parameters
   (?p - person
    ?e - exhibit)
  :duration (= ?duration (time-to-see ?e))
  :condition
   (and (over all (at ?p ?e)) (at start (want-to-see ?e)) (over all (open)))
  :effect
   (and (at start (not (want-to-see ?e))) (at start (increase (seen) (excitement ?e))))
)


)