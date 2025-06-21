INSERT INTO "user" (account_non_expired, account_non_locked, credentials_non_expired, enabled, id, email, password) VALUES (true, true, true, true, RANDOM_UUID(), 'admin@email.com', '$2a$10$PDIkEfJysuBypngQBn00MutewMs5qmnbGNnb0spCLYCRcC35YGO/2');
INSERT INTO "user" (account_non_expired, account_non_locked, credentials_non_expired, enabled, id, email, password) VALUES (true, true, true, true, RANDOM_UUID(), 'guest@email.com', '$2a$10$eBjBfkT2Gb9JHSObuFGtkO7wQW20d1pUaDYKKKiVy/Y55hq7lnJlG');

-- Insert goal
INSERT INTO goal(deadline, target_weight, id, user_id, description)
VALUES (
           DATE '2025-12-31',
           75.0,
           RANDOM_UUID(),
           (SELECT id FROM "user" WHERE email = 'admin@email.com' LIMIT 1),
           'Lose 5kg before the end of the year'
       );

-- Insert muscle groups
INSERT INTO muscle_group (id, name)
VALUES
    (RANDOM_UUID(), 'Chest'),
    (RANDOM_UUID(), 'Back'),
    (RANDOM_UUID(), 'Legs'),
    (RANDOM_UUID(), 'Arms'),
    (RANDOM_UUID(), 'Shoulders'),
    (RANDOM_UUID(), 'Glutes'),
    (RANDOM_UUID(), 'Hamstrings'),
    (RANDOM_UUID(), 'Quadriceps'),
    (RANDOM_UUID(), 'Biceps'),
    (RANDOM_UUID(), 'Triceps'),
    (RANDOM_UUID(), 'Lats'),
    (RANDOM_UUID(), 'Core');

-- Insert exercises
INSERT INTO exercise (id, name, description, difficulty, equipment)
VALUES
    (RANDOM_UUID(), 'Bench Press', 'Chest exercise using barbell or dumbbells', 'Medium', 'Barbell'),
    (RANDOM_UUID(), 'Squat', 'Compound leg exercise with barbell', 'Hard', 'Barbell'),
    (RANDOM_UUID(), 'Bicep Curl', 'Arm isolation exercise using dumbbells', 'Easy', 'Dumbbells'),
    (RANDOM_UUID(), 'Deadlift', 'Back and leg exercise using barbell', 'Hard', 'Barbell'),
    (RANDOM_UUID(), 'Overhead Press', 'Shoulder exercise pressing weight overhead', 'Medium', 'Barbell'),
    (RANDOM_UUID(), 'Lat Pulldown', 'Back exercise pulling a bar downward', 'Easy', 'Cable Machine'),
    (RANDOM_UUID(), 'Leg Press', 'Lower body compound movement using a machine', 'Medium', 'Leg Press Machine'),
    (RANDOM_UUID(), 'Tricep Pushdown', 'Arm isolation exercise using a cable machine', 'Easy', 'Cable Machine'),
    (RANDOM_UUID(), 'Romanian Deadlift', 'Hamstring-focused barbell exercise', 'Medium', 'Barbell'),
    (RANDOM_UUID(), 'Seated Row', 'Back exercise using a rowing machine', 'Medium', 'Cable Machine'),
    (RANDOM_UUID(), 'Lateral Raise', 'Shoulder isolation movement with dumbbells', 'Easy', 'Dumbbells'),
    (RANDOM_UUID(), 'Pull-Up', 'Bodyweight back and arm exercise', 'Hard', 'Bodyweight'),
    (RANDOM_UUID(), 'Hip Thrust', 'Glute exercise using a barbell or machine', 'Medium', 'Barbell'),
    (RANDOM_UUID(), 'Chest Fly', 'Chest isolation movement using a cable machine or dumbbells', 'Easy', 'Cable Machine');

-- Insert into exercise_muscle_group (no change needed; valid in H2)
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

-- Insert workouts
INSERT INTO workout (id, name, description)
VALUES
    (RANDOM_UUID(), 'Full Body Routine', 'A general strength-focused full body workout'),
    (RANDOM_UUID(), 'Leg Day Routine', 'Focus on building lower body strength'),
    (RANDOM_UUID(), 'Arm Blast', 'High-rep upper arm hypertrophy workout');

-- Insert workout sessions
INSERT INTO workout_session (id, user_id, workout_id, date, notes, status)
VALUES
    (RANDOM_UUID(), (SELECT id FROM "user" WHERE email = 'admin@email.com' LIMIT 1),
     (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1), DATE '2025-05-12', 'Felt strong today', 'COMPLETED'),
    (RANDOM_UUID(), (SELECT id FROM "user" WHERE email = 'admin@email.com' LIMIT 1),
     (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1), DATE '2025-06-10', 'Heavy squat sets', 'COMPLETED'),
    (RANDOM_UUID(), (SELECT id FROM "user" WHERE email = 'admin@email.com' LIMIT 1),
     (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1), DATE '2025-06-11', 'Great pump today', 'SCHEDULED');

-- Insert workout sets
INSERT INTO workout_set (id, workout_id, exercise_id, reps, weight)
VALUES
    (RANDOM_UUID(), (SELECT id FROM workout WHERE name = 'Full Body Routine' LIMIT 1),
     (SELECT id FROM exercise WHERE name = 'Bench Press' LIMIT 1), 10, 80.0),
    (RANDOM_UUID(), (SELECT id FROM workout WHERE name = 'Leg Day Routine' LIMIT 1),
     (SELECT id FROM exercise WHERE name = 'Squat' LIMIT 1), 8, 100.0),
    (RANDOM_UUID(), (SELECT id FROM workout WHERE name = 'Arm Blast' LIMIT 1),
     (SELECT id FROM exercise WHERE name = 'Bicep Curl' LIMIT 1), 12, 15.0);
