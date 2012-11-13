tvschedule
==========
Project description:

You are to implement a very simple web application that captures and consolidates weekly syndicated TV schedules from structured user input.
Your web form will accept an arbitrary number of rows with the following fields:

Show Name
Days of Week
Time of Day
Duration

The system should attempt to create the most efficient representation of the schedule possible.  That means if you see two entries like this:

Car Racing
Monday
6pm
1hr

Car Racing
Tuesday
6pm
1hr

You would store only one representation - Car Racing, M/T, 6pm, 1hr (consolidating the days).  Similarly:

Car Racing
Monday
6pm
1hr

Car Racing
Monday
7pm
2hr

Would be compacted to Car Racing, M, 6pm, 3hr (consolidating the contiguous times).  Always prefer the greatest consolidation option available.

Bonus if your application detects collisions - when different shows are defined over the same times and days.