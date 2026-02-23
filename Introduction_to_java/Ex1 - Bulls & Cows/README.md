# Ex1 - Bulls & Cows (Automatic Solution)

Course: Introduction to Computer Science, Ariel University

## Reference

https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true

## Overview

Bulls & Cows is a code-guessing game where the goal is to find a secret code with 2-6 digits.
After each guess, the server returns:
- Bulls: correct digits in the correct positions.
- Cows: correct digits in the wrong positions.

Using this feedback, the solver narrows the search until it finds the secret code.

## General Solution (Algorithm)

The solver keeps a boolean array of all possible guesses, where each index represents a candidate code.

1. Start with an initial guess.
2. Receive Bulls/Cows feedback from the server.
3. Eliminate all candidates that would not produce the same feedback for that guess.
4. Choose the next remaining candidate and repeat.
5. Continue until the code is found.

## Results

Average number of guesses by code length:
- 2 digits: 6.65
- 3 digits: 7.64
- 4 digits: 8.22
- 5 digits: 8.75
- 6 digits: 9.22
