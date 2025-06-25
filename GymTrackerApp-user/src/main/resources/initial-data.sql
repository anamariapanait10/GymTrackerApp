INSERT INTO goal(deadline, target_weight, id, user_id, description)
VALUES ('2025-12-31', 75.0, UNHEX(REPLACE(UUID(), '-', '')),
        (SELECT id FROM user WHERE username = 'admin' LIMIT 1), 'Lose 5kg before the end of the year');

INSERT INTO muscle_group (id, name)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Chest'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Back'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Legs'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Arms'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Shoulders'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Glutes'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Hamstrings'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Quadriceps'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Biceps'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Triceps'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Lats'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Core');

INSERT INTO exercise (id, name, description, difficulty, equipment)
VALUES
    (UNHEX(REPLACE(UUID(), '-', '')), 'Bench Press', 'Chest exercise using barbell or dumbbells', 'Medium', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Squat', 'Compound leg exercise with barbell', 'Hard', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Bicep Curl', 'Arm isolation exercise using dumbbells', 'Easy', 'Dumbbells'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Deadlift', 'Back and leg exercise using barbell', 'Hard', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Overhead Press', 'Shoulder exercise pressing weight overhead', 'Medium', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Lat Pulldown', 'Back exercise pulling a bar downward', 'Easy', 'Cable Machine'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Leg Press', 'Lower body compound movement using a machine', 'Medium', 'Leg Press Machine'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Tricep Pushdown', 'Arm isolation exercise using a cable machine', 'Easy', 'Cable Machine'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Romanian Deadlift', 'Hamstring-focused barbell exercise', 'Medium', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Seated Row', 'Back exercise using a rowing machine', 'Medium', 'Cable Machine'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Lateral Raise', 'Shoulder isolation movement with dumbbells', 'Easy', 'Dumbbells'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Pull-Up', 'Bodyweight back and arm exercise', 'Hard', 'Bodyweight'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Hip Thrust', 'Glute exercise using a barbell or machine', 'Medium', 'Barbell'),
    (UNHEX(REPLACE(UUID(), '-', '')), 'Chest Fly', 'Chest isolation movement using a cable machine or dumbbells', 'Easy', 'Cable Machine');

INSERT INTO exercise_muscle_group (exercise_id, muscle_group_id)
VALUES
((SELECT id FROM exercise WHERE name = 'Bench Press'), (SELECT id FROM muscle_group WHERE name = 'Chest')),
((SELECT id FROM exercise WHERE name = 'Bench Press'), (SELECT id FROM muscle_group WHERE name = 'Triceps')),
((SELECT id FROM exercise WHERE name = 'Bench Press'), (SELECT id FROM muscle_group WHERE name = 'Shoulders')),
((SELECT id FROM exercise WHERE name = 'Squat'), (SELECT id FROM muscle_group WHERE name = 'Legs')),
((SELECT id FROM exercise WHERE name = 'Squat'), (SELECT id FROM muscle_group WHERE name = 'Glutes')),
((SELECT id FROM exercise WHERE name = 'Squat'), (SELECT id FROM muscle_group WHERE name = 'Quadriceps')),
((SELECT id FROM exercise WHERE name = 'Squat'), (SELECT id FROM muscle_group WHERE name = 'Core')),
((SELECT id FROM exercise WHERE name = 'Bicep Curl'), (SELECT id FROM muscle_group WHERE name = 'Arms')),
((SELECT id FROM exercise WHERE name = 'Bicep Curl'), (SELECT id FROM muscle_group WHERE name = 'Biceps')),
((SELECT id FROM exercise WHERE name = 'Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Back')),
((SELECT id FROM exercise WHERE name = 'Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Glutes')),
((SELECT id FROM exercise WHERE name = 'Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Hamstrings')),
((SELECT id FROM exercise WHERE name = 'Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Core')),
((SELECT id FROM exercise WHERE name = 'Overhead Press'), (SELECT id FROM muscle_group WHERE name = 'Shoulders')),
((SELECT id FROM exercise WHERE name = 'Overhead Press'), (SELECT id FROM muscle_group WHERE name = 'Triceps')),
((SELECT id FROM exercise WHERE name = 'Lat Pulldown'), (SELECT id FROM muscle_group WHERE name = 'Back')),
((SELECT id FROM exercise WHERE name = 'Lat Pulldown'), (SELECT id FROM muscle_group WHERE name = 'Lats')),
((SELECT id FROM exercise WHERE name = 'Lat Pulldown'), (SELECT id FROM muscle_group WHERE name = 'Biceps')),
((SELECT id FROM exercise WHERE name = 'Leg Press'), (SELECT id FROM muscle_group WHERE name = 'Legs')),
((SELECT id FROM exercise WHERE name = 'Leg Press'), (SELECT id FROM muscle_group WHERE name = 'Quadriceps')),
((SELECT id FROM exercise WHERE name = 'Leg Press'), (SELECT id FROM muscle_group WHERE name = 'Glutes')),
((SELECT id FROM exercise WHERE name = 'Tricep Pushdown'), (SELECT id FROM muscle_group WHERE name = 'Arms')),
((SELECT id FROM exercise WHERE name = 'Tricep Pushdown'), (SELECT id FROM muscle_group WHERE name = 'Triceps')),
((SELECT id FROM exercise WHERE name = 'Romanian Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Hamstrings')),
((SELECT id FROM exercise WHERE name = 'Romanian Deadlift'), (SELECT id FROM muscle_group WHERE name = 'Glutes')),
((SELECT id FROM exercise WHERE name = 'Seated Row'), (SELECT id FROM muscle_group WHERE name = 'Back')),
((SELECT id FROM exercise WHERE name = 'Seated Row'), (SELECT id FROM muscle_group WHERE name = 'Lats')),
((SELECT id FROM exercise WHERE name = 'Seated Row'), (SELECT id FROM muscle_group WHERE name = 'Biceps')),
((SELECT id FROM exercise WHERE name = 'Lateral Raise'), (SELECT id FROM muscle_group WHERE name = 'Shoulders')),
((SELECT id FROM exercise WHERE name = 'Pull-Up'), (SELECT id FROM muscle_group WHERE name = 'Back')),
((SELECT id FROM exercise WHERE name = 'Pull-Up'), (SELECT id FROM muscle_group WHERE name = 'Lats')),
((SELECT id FROM exercise WHERE name = 'Pull-Up'), (SELECT id FROM muscle_group WHERE name = 'Biceps')),
((SELECT id FROM exercise WHERE name = 'Hip Thrust'), (SELECT id FROM muscle_group WHERE name = 'Glutes')),
((SELECT id FROM exercise WHERE name = 'Hip Thrust'), (SELECT id FROM muscle_group WHERE name = 'Hamstrings')),
((SELECT id FROM exercise WHERE name = 'Chest Fly'), (SELECT id FROM muscle_group WHERE name = 'Chest')),
((SELECT id FROM exercise WHERE name = 'Chest Fly'), (SELECT id FROM muscle_group WHERE name = 'Shoulders'));


INSERT INTO workout (id, name, description)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'Full Body Routine', 'A general strength-focused full body workout'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Leg Day Routine', 'Focus on building lower body strength'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Arm Blast', 'High-rep upper arm hypertrophy workout');

INSERT INTO workout_session (id, user_id, workout_id, date, notes, status)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE username = 'admin' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1), '2025-05-12', 'Felt strong today',
        'COMPLETED'),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE username = 'admin' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1), '2025-06-10', 'Heavy squat sets', 'COMPLETED'),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM user WHERE username = 'admin' LIMIT 1),
        (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1), '2025-06-11', 'Great pump today', 'SCHEDULED');

INSERT INTO workout_set (id, workout_id, exercise_id, reps, weight)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1), 10, 80.0),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Squat' LIMIT 1), 8, 100.0),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1),
        (SELECT id FROM exercise WHERE name = 'Bicep Curl' LIMIT 1), 12, 15.0);
