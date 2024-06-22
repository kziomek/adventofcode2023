Part 2 requires mapping ranges to destination ranges as presented in following example

seeds														82
55 67
79 92

seed to soil												
55 67 -> (52 50 48) ->  57 69
79 92 -> (52 50 48) ->  81 94                               84


soil to fertilizer											
57 69 -> 57 69
81 94 -> 81 94                                              84

Fertilizer to water											
57 69 -> 57 60 -> 53 56
61 69 -> 61 69
81 94                                                       84

Water to light 											    
53 56 -> (18 25 70) -> 46 49
61 69 -> (18 25 70) -> 54 62
81 94 -> (18 25 70) -> 74 87                                77

Light to Temperature										
46 49 -> (81 45 19) -> 82 85
54 62 -> (81 45 19) -> 90 98
74 87 -> 74 76 -> (68 64 13) -> 78 80
74 87 -> 77 87 -> (45 77 23) -> 45 55						        45

Temperature to Humidity										
45 55 -> (1 0 69) -> 46 56						            46
78 80
82 85
90 98

Humidity to Locations										
46 56 -> 46 55                                                       46
46 56 -> 56 -> (60 56 37) -> 60
78 80 -> (60 56 37) -> 82 84
82 85 -> (60 56 37) -> 86 89
90 98 -> 90 92 -> (60 56 37) -> 94 96
90 98 -> 93 96 -> (56 93 4)  -> 56 59
90 98 => 97 98

Locations
46 55                                                    46 - lowest location (it's for seed 82)
56 59
60 60
82 84
86 89
94 96
97 98
