# Conventions

This file contains informaiton about all conventions that the development has to be compliant with.

## Code Style conventions

The project will be developed according to the Google Java Styleguide, that can be found [here](https://google.github.io/styleguide/javaguide.html).

Styleguide files for all common IDEs can be found [here](https://github.com/google/styleguide).

## Branching

Each change to the code of the project will not be done in the master branch. If a new feature is implemented or a bug is fixed a new branch has to be created. After the implementation or the fix is completed and tested, the development branch can be merged with a pull request.

A branch must not be merged into the master branch if the TravisCI build is not successful.

The naming conventions for the new branches are as follows:

Reason | Naming
------ | ------
Feature | `feature/<featureName>`
Bugfix | `bugfix/<issueNumber>`

## Testing

All unit testing is supposed to be done with JUnit5. All written code is to be tested aiming for a code coverage of 100 % for all classes.

Unit testing will be an integral part of the automated build process and each build with failing unit tests will be considered a failed build. Therefore it should be avoided to push non working unit tests.
